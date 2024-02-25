using System.Security.Cryptography;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Receptai.Server.Models;

namespace Receptai.Server.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController: ControllerBase
{
    private readonly AppDbContext _context;

    private const int SaltSize = 16;
    private const int OutputSize = 32;
    private const int Iterations = 50000;

    public UserController(AppDbContext context)
    {
        _context = context;
    }
    
    /// <summary>
    /// Logins with the specified email and password.
    /// </summary>
    /// <returns></returns>
    [HttpPost("login")]
    public async Task<ActionResult<string[]>> Login(string email, string password)
    {
        var user = await _context.Users.SingleOrDefaultAsync(u => u.Email == email);

        if (user == null)
            return NotFound("Vartotojas nerastas.");

        var computedHash =
            Rfc2898DeriveBytes.Pbkdf2(password, user.Salt, Iterations, HashAlgorithmName.SHA256, OutputSize);
        
        if (!CryptographicOperations.FixedTimeEquals(computedHash, user.PasswordHash))
        {
            return NotFound("Vartotojas nerastas.");
        }


        // Create new user token
        var userToken = new UserToken()
        {

        };

        _context.UserTokens.Add(userToken);
        await _context.SaveChangesAsync();
        
        
        // TODO: set the cookies
        
        Response.Cookies.Append("token", "a");

        return Ok("Prisijungta sėkmingai.");
    }
    
    /// <summary>
    /// Registers to the server with the specified username, email and password.
    /// </summary>
    /// <returns></returns>
    [HttpPost("register")]
    public async Task<ActionResult<string[]>> Register(string username, string email, string password)
    {
        // Check if username or email is already registered
        if (await _context.Users.AnyAsync(u => u.Username == username))
            return Conflict("Vartotojas tokiu vardu jau egzistuoja.");
        if (await _context.Users.AnyAsync(u => u.Email == email))
            return Conflict("Vartotojos tokiu el. paštu jau egzistuoja.");

        var salt = RandomNumberGenerator.GetBytes(SaltSize);
        var hash = Rfc2898DeriveBytes.Pbkdf2(password, salt, Iterations, HashAlgorithmName.SHA256, OutputSize);
        
        var user = new User()
        {
            Username = username,
            Email = email,
            Salt = salt,
            PasswordHash = hash,
            Role = UserRoles.Member
        };
        
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        return Ok("Vartotojas užregistruotas sėkmingai.");
    }
    
    /// <summary>
    /// Logs out of the current user account.
    /// </summary>
    /// <returns></returns>
    [HttpGet("logout")]
    public async Task<ActionResult<string[]>> Logout()
    {
        var token = Request.Cookies["token"];
        if (token == null)
            return BadRequest("Token not found");
        
        // TODO: remove cookie from UserTokens
        
        Response.Cookies.Delete("token");
        
        return Ok("Atsijungta nuo paskyros.");
    }
    
    /// <summary>
    /// Returns information about the current user.
    /// </summary>
    /// <returns></returns>
    [HttpGet("me")]
    public string[] GetCurrentUser()
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
}
using Microsoft.AspNetCore.Mvc;

namespace Receptai.Server.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController: ControllerBase
{
    
    /// <summary>
    /// Logins with the specified email and password.
    /// </summary>
    /// <returns></returns>
    [HttpPost("login")]
    public string[] Login(string email, string password)
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
    
    /// <summary>
    /// Registers to the server with the specified username, email and password.
    /// </summary>
    /// <returns></returns>
    [HttpPost("register")]
    public string[] Register(string username, string email, string password)
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
    
    /// <summary>
    /// Logs out of the current user account.
    /// </summary>
    /// <returns></returns>
    [HttpGet("logout")]
    public string[] Logout()
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
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
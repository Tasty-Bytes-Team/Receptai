using Microsoft.AspNetCore.Mvc;

namespace Receptai.Server.Controllers;

[ApiController]
[Route("api/v1/[controller]")]
public class UserController: ControllerBase
{
    
    /// <summary>
    /// Returns information about the current user.
    /// </summary>
    /// <returns></returns>
    [HttpGet(Name = "GetCurrentUser")]
    public string[] GetCurrentUser()
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
    
    /// <summary>
    /// Returns information about the specified user.
    /// </summary>
    /// <param name="id">User ID.</param>
    /// <returns>Something else</returns>
    [HttpGet("{id}")]
    public string[] GetUserById(ulong id)
    {
        return new string[] { "can you believe it", id.ToString() };
    }
    
        
    [HttpPost(Name = "PostXd")]
    public string[] Post()
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
}
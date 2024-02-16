using Microsoft.AspNetCore.Mvc;

namespace Receptai.Server.Controllers;

[ApiController]
[Route("api/v1/[controller]jkhhjkj")]
public class TestingController : ControllerBase
{
    
    [HttpGet(Name = "GetTest")]
    public string[] Get()
    {
        return new string[] { "vacius", "jusas", "pagarbiai" };
    }
    
}
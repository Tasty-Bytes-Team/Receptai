using Microsoft.AspNetCore.Identity;

namespace Receptai.Server.Models;

public class User
{
    public long Id { get; set; }
    //public required string Username { get; set; }
    
    //public required string Email { get; set; }
    //public required byte[] PasswordHash { get; set; }
    //public required byte[] Salt { get; set; }
    //public required UserRoles Role { get; set; }
    public string? Biography { get; set; }
    public Media? ProfilePicture { get; set; }
}

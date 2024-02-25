using System.ComponentModel.DataAnnotations;

namespace Receptai.Server.Models;

public class User
{
    public long Id { get; set; }
    
    [Required]
    public string Username { get; set; }
    
    [Required]
    public required string Email { get; set; }
    
    [Required]
    public required byte[] PasswordHash { get; set; }
    
    [Required]
    public required byte[] Salt { get; set; }
    
    [Required]
    public required UserRoles Role { get; set; }
    
    public string? Biography { get; set; }
    
    public Media? ProfilePicture { get; set; }
    
}

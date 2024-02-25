using System.ComponentModel.DataAnnotations;

namespace Receptai.Server.Models;

public class UserToken
{
    public long Id { get; set; }
    // TODO: specify the user it belongs to
    
    [Required]
    public User User { get; set; }
    
    [Required]
    public DateTime DateExpires { get; set; }
}

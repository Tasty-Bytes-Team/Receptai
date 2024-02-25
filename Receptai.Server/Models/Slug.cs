using System.ComponentModel.DataAnnotations;

namespace Receptai.Server.Models;

public class Slug
{
    public int Id { get; set; }
    
    [Required]
    public string Name { get; set; }
}
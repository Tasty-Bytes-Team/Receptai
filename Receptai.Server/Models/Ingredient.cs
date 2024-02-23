namespace Receptai.Server.Models;

public class Ingredient
{
    public long Id { get; set; }
    public required string Name { get; set; } = null!;
}
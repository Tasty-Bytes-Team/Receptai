using System.ComponentModel.DataAnnotations;

namespace Receptai.Server.Models;

public class Recipe
{
    public long Id { get; set; }
    public virtual User Author { get; set; } = null!;
    public required string Title { get; set; }
    public required string Description { get; set; }

    // del ingredientu strukturos dar neaisku
    public ICollection<Ingredient> Ingredients { get; set; }
    
    public ICollection<RecipeStep> RecipeSteps { get; set; }
    
    public ICollection<Category> Categories { get; set; }
    
    public Media PreviewImage { get; set; }
    
    public DateTime DateCreated { get; set; }
    public DateTime DateModified { get; set; }
}
using Microsoft.EntityFrameworkCore;

namespace Receptai.Server.Models;

public class AppDbContext: DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<User> Users { get; set; } = null!;
    public DbSet<UserToken> UserTokens { get; set; } = null!;
    //public DbSet<Recipe> Recipes { get; set; } = null!;
    //public DbSet<Ingredient> Ingredients { get; set; } = null!;
    //public DbSet<File> Files { get; set; } = null!;
}
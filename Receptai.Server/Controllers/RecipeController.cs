using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Receptai.Server.Models;

namespace Receptai.Server.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class RecipeController : ControllerBase
    {
        private readonly Models.DbContext _context;
        private const int RecipesPerPage = 20;

        public RecipeController(Models.DbContext context)
        {
            _context = context;
        }

        public record PaginatedRecipeResult
        {
            public int CurrentPage { get; set; }
            public int RecipesPerPage { get; set; }
            public int PageCount { get; set; }
            public IEnumerable<Recipe> Recipes { get; set; }
        }
        
        
        [HttpGet("list/{page}")]
        public async Task<ActionResult<PaginatedRecipeResult>> GetRecipesInPage(int page)
        {
            if (page <= 0) return BadRequest();

            var skipRows = (page - 1) * RecipesPerPage;
            var nextPage = await _context.Recipes
                .OrderBy(b => b.Id)
                .Skip(skipRows)
                .Take(RecipesPerPage)
                .ToListAsync();
            
            return new PaginatedRecipeResult
            {
                CurrentPage = page,
                RecipesPerPage = RecipesPerPage,
                PageCount = await _context.Recipes.CountAsync(), // TODO: divide by some value
                Recipes = nextPage,
            };
        }
        
        [HttpGet("{id}")]
        public async Task<ActionResult<Recipe>> GetRecipeById(long id)
        {
            var recipe = await _context.Recipes.FindAsync(id);

            if (recipe == null)
            {
                return NotFound();
            }

            return recipe;
        }

        // POST: api/Recipe
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Recipe>> PostRecipe(string title, string description)
        {
            var recipe = new Recipe()
            {
                Title = title,
                Description = description,
                DateCreated = DateTime.Now
            };
            
            _context.Recipes.Add(recipe);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetRecipeById", new { id = recipe.Id }, recipe);
        }
    }
}


using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Builder;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Receptai.Server.Models;
using DbContext = Receptai.Server.Models.DbContext;

namespace Receptai.Server
{
	public class Program
	{
		public static void Main(string[] args)
		{
			var builder = WebApplication.CreateBuilder(args);

			// Add services to the container.

			builder.Services.AddRouting(options => options.LowercaseUrls = true);
			builder.Services.AddControllers();
			builder.Services.AddDbContext<DbContext>(opt =>
				opt.UseInMemoryDatabase("DatabaseName"));
			
			// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
			builder.Services.AddEndpointsApiExplorer();
			builder.Services.AddSwaggerGen();

			var app = builder.Build();

			app.UseDefaultFiles();
			app.UseStaticFiles();

			// Configure the HTTP request pipeline.
			if (app.Environment.IsDevelopment())
			{
				app.UseSwagger();
				app.UseSwaggerUI();
			}

			app.UseHttpsRedirection();

			//app.UseAuthentication();
			app.UseAuthorization();

			app.MapControllers();

			app.MapFallbackToFile("/index.html");

			app.Run();
		}
	}
}

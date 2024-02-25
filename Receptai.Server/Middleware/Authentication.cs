namespace Receptai.Server.Middleware;

public class CustomAuthenticationMiddleware
{
    private readonly RequestDelegate _next;

    public CustomAuthenticationMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        Console.WriteLine($"Calling CustomAuthenticationMiddleware before {context}");
        
        /*var cultureQuery = context.Request.Query["culture"];
        if (!string.IsNullOrWhiteSpace(cultureQuery))
        {
            var culture = new CultureInfo(cultureQuery);

            CultureInfo.CurrentCulture = culture;
            CultureInfo.CurrentUICulture = culture;
        }*/

        // Call the next delegate/middleware in the pipeline.
        await _next(context);
    }
}

public static class CustomAuthenticationMiddlewareExtensions
{
    public static IApplicationBuilder UseCustomAuthentication(
        this IApplicationBuilder builder)
    {
        return builder.UseMiddleware<CustomAuthenticationMiddleware>();
    }
}
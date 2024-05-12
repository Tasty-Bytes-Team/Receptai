import type { Recipe } from "./types";

const printDownload = (recipe: Recipe) => {
  const w = window.open();

  if (!w) {
    console.error("Failed to open new window for printing recipe.");
    return;
  }

  const printablePage = w.document;

  printablePage.write(`
  <!DOCTYPE html>
  <html>
    <head>
      <title>Recipe: ${recipe.name}</title>
      <style>
        body {
          font-family: 'Roboto', sans-serif;
          margin: 1em;
        }
        h1, h2, h3, h4, h5 {
          margin: .5em 0 .5em 0;
        }
        p {
          margin: 1em 0 1em 0;
        }
        .short-description {
            margin: .7em 0 .7em 0;
        }
        .steps-text {
            margin: .7em 0 .7em 0;
        }
        .tags {
            margin: 0;
        }
        ul {
            margin-top: 0;
          list-style: disc;
          padding-left: 1em;
        }
      </style>
    </head>
    <body>
  `);

  printablePage.write("</head><body>");
  printablePage.write(`<h1 style="font-size: 2.5em">Tasty Bytes recipes</h1>`);
  printablePage.write("<div>");
  printablePage.write(`<h1>${recipe.name}</h1>`);
  printablePage.write(
    `<p><b>${recipe.author.name}</b>, ${recipe.dateCreated.split("T")[0]}</p>`
  );
  printablePage.write("</div>");
  printablePage.write(
    `<p class="short-description">${recipe.shortDescription}</p>`
  );

  printablePage.write("<div>");
  printablePage.write(
    `<p class="tags"><b>Preparation time:</b> ${recipe.minutesToPrepare} minutes</p>`
  );
  printablePage.write(
    `<p class="tags"><b>Number of servings: </b>${recipe.portions}</p>`
  );
  printablePage.write("</div>");

  printablePage.write("<h2>Ingredients</h2>");

  recipe.ingredients.forEach((ingredientGroup, index) => {
    printablePage.write("<div>");
    printablePage.write(`<h3>${ingredientGroup.purpose}</h3>`);

    printablePage.write("<ul>");
    ingredientGroup.ingredients.forEach((ingredient, index) => {
      printablePage.write(
        `<li>${ingredient.name}, ${ingredient.quantity}, ${ingredient.unit}</li>`
      );
    });
    printablePage.write("</ul>");

    printablePage.write("</div>");
  });

  printablePage.write("<div>");
  printablePage.write("<h2>Method</h2>");
  recipe.instructions.forEach((instruction, index) => {
    printablePage.write(`<h3>STEP ${index + 1}</h3>`);

    printablePage.write(`<p class="steps-text">${instruction.text}</p>`);
  });
  printablePage.write("</div>");

  printablePage.write("</body></html>");

  w.print();
  w.close();
};

export default printDownload;

type GetRecipes = () => void;

const sortOptionSelector = (selectionValue: string) => {
  switch (selectionValue) {
    case "nameDesc":
      return ["dateCreated", false];
    case "nameAsc":
      return ["dateCreated", true];
    case "DateDesc":
      return ["name", false];
    case "DateAsc":
      return ["name", true];
  }
};

export default sortOptionSelector;

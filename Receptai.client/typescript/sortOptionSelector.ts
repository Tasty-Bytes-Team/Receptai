const sortOptionSelector = (selectionValue: string) => {
  switch (selectionValue) {
    case "DateDesc":
      return ["dateCreated", false];
    case "DateAsc":
      return ["dateCreated", true];
    case "nameDesc":
      return ["name", false];
    case "nameAsc":
      return ["name", true];
  }
};

export default sortOptionSelector;

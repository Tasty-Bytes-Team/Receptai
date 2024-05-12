export interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}
export interface User {
  id: number;
  name: string;
  email: string;
  avatarUrl: string | null;
}

export interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo?: string;
  tutorialVideoEmbed?: string;
  ingredients: Ingredients[];
  instructions: Instruction[];
  tags: Tag[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
  averageRating: number;
}

export interface PostRecipe {
  name: string;
  shortDescription: string;
  previewImage: string;
  tutorialVideo: string | null;
  ingredients: Ingredients[];
  instructions: string[];
  minutesToPrepare: number | null;
  portions: number | null;
  categoryId: number | string;
  tagIds: number[] | string[];
}

export interface Instruction {
  text: string;
}

export interface Tag {
  id: number;
  name: string;
  iconName: string;
}

export interface Author {
  id: number;
  name: string;
  avatarUrl: string | null;
}

export interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

export interface Ingredient {
  name: string;
  quantity: number | null;
  unit: string;
}

export interface Category {
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

export interface Review {
  content: string;
  dateCreated: string;
  rating: number;
  recipe: Recipe;
  user: User;
}

export interface Link {
  name: string;
  link: string;
}

export interface Navigation {
  name: string;
  links: Link[];
}

export interface Tag {
  id: number;
  name: string;
  iconName: string;
}

export interface RecipeContainerColumn {
  key: string;
  label: string;
  sortable: boolean;
  sortBy?: string;
  curr?: boolean;
}

export interface Feedback {
  content: string;
  rating: number;
  dateCreated: string;
  user: User;
}

export interface HeaderNavigation {
  to: string;
  title: string;
  highlight?: boolean;
  onlyForMobile?: boolean;
}

export interface Message {
  text: string;
  show: boolean;
  label?: "Error" | "Success";
  links?: Link[];
}

export interface Link {
  text: string;
  link: string;
  type?: "Black" | "Gray";
}

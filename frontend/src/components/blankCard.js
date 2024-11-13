import RecipeCard from './recipeCard.js';

export default function BlankCard() {
    const blankRecipe = {
        title: "Add a New Recipe",
        ingredients: [],
    }
    
    return (
        <RecipeCard recipe={blankRecipe} />
    )
}
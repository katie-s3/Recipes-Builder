import RecipeCard from '../components/recipeCard.js';
import BlankRecipe from '../components/blankCard.js';

import {useNavigate} from 'react-router-dom';

export default function RecipesGrid(props) {
    const navigate = useNavigate();  

    const displayRecipes = (props) => {
      const { recipes } = props;

      const handleClick = (recipe) => {
        if (recipe.title === 'Add a New Recipe') {
          alert("TODO: Add new recipe");
        }
        else {
            navigate(`/details/${recipe.title}`, { state: { recipe } });
        }
      }
  
      if (recipes.length > 0) {
  
        return recipes.map((recipe, index) => {
  
          return (
            <>
                <RecipeCard recipe={recipe} onClick={() => handleClick(recipe)}/>
            </>
              
            );
          })
      }
        else {
          return (
            <div>
            </div>
          )
        }
    }
  
    return (
      <>
        <div 
          className="grid-container" 
          style={{
              display: "grid", 
              "gridTemplateColumns": "auto auto auto", 
              gap: "15px 15px", 
              marginTop: "15px"
          }}
        >
            <BlankRecipe />
          { displayRecipes(props) }
        </div>
      </>
      
    )
  }
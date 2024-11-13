import React, {useEffect, useState} from 'react';
import { useLocation, useParams } from 'react-router-dom';

export default function RecipeDetails() {
    const location = useLocation();
    const title = useParams();
    const { recipe } = location.state || {};

    if (!recipe) {
        return <div>No recipe found with title {title} </div>
    }

    const instructionsList = recipe.instructions.split("\n");
    const instructions = instructionsList.filter(item => item.trim() !== "");

    return (
        <>
            <div
            style={{margin:'30px'}}
            >
                <h3
                >
                    { recipe.title }
                </h3>
                <p />
                <h4>Ingredients</h4>
                <ul>
                    { recipe.ingredients.map((ingredient, index) => (
                        <li key={index}>{ingredient}</li>
                    ))}
                </ul>
                <p />
                <h4>Instructions</h4>
                <ol>
                    { instructions.map((instruction, index) => (
                        <li key={index}>{instruction}</li>
                    ))}
                </ol>
            </div>
        </>
    )
}
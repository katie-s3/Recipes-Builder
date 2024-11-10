import Reach, { useEffect, useState } from 'react';
import RecipeCard from '../components/recipeCard';

export default function Home() {

    return (
        <>
            <div className="container">
                <h1>
                    Hello!
                </h1>
            </div>

            <div>
                < RecipeCard margin="18rem"/>
            </div>
        </>
        
    )
}
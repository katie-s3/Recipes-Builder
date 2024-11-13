import React, { useEffect, useState } from 'react';
import RecipesGrid from '../components/recipesGrid.js';

export default function Home() {

    const [recipes, setRecipes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const baseUrl = process.env.REACT_APP_BASE_URL;

    useEffect(() => {
        const url = baseUrl + "/recipes/all";
        fetch(url)
        .then((response) => response.json())
        .then((data) => {
            setRecipes(data);
            setLoading(false);
        })
        .catch((error) => {
            setError(error);
            setLoading(false);
            });
    }, []);

    
    if (loading) {
        return <div>Loading...</div>;
    }
    else if (error) {
        return <div>An error occurred. Please try again later. </div>; 
    }

    else {
        return (
            <>
                <div className="container">
                    <h1>
                        Hello!
                    </h1>
                </div>

                <div>
                    < RecipesGrid recipes={recipes}/>
                </div>
            </>
        )
    }
    
}
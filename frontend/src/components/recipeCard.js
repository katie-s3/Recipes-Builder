import Card from 'react-bootstrap/Card';

import '../styles.css';


export default function RecipeCard({ recipe, onClick}) {
  
  return (
    <Card 
      style={{ width: '20rem', margin: "4rem", boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', cursor:'pointer' }}
      border="dark"
      onClick={onClick}
    >
      <div className="d-flex justify-content-center">
        <Card.Img 
          className="icon img-fluid" 
          variant="top" 
          src="/cookbook-default.png" 
          style={{
            width: "75%",
            height: "75%",
            padding:"15px",
            
          }}
        />
      </div>
      
      <Card.Footer>
        <Card.Title><h4>{ recipe.title }</h4></Card.Title>
        <Card.Text> { recipe.ingredients.length } Ingredients | { recipe.time } Minutes</Card.Text>
      </Card.Footer>
    </Card>
  )
}
  

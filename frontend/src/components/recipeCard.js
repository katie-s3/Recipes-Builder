import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

import '../styles.css';


export default function RecipeCard() {
  return (
    <Card 
      style={{ width: '18rem', margin: "4rem", boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)' }}
      border="dark"
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
        <Card.Title><h3>Recipe Title</h3></Card.Title>
        <Card.Text> # Ingredients | Total Time</Card.Text>
      </Card.Footer>
    </Card>
  );
}


import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter, useRoutes } from 'react-router-dom';

import Home from './pages/homePage';

const AppRoutes = () => {
  const routes = useRoutes([
    { path: '/', element: <Home />}
  ]); 

  return routes;
}

function App() {
  return (
    <BrowserRouter>
      <AppRoutes />
    </BrowserRouter>
  );
}

export default App;

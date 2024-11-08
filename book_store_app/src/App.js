import { BrowserRouter, Route, Routes } from 'react-router-dom'
import SignIn from './pages/Signin'
import SignUp from './pages/SignUp'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/auth" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/forget" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
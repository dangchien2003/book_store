import { BrowserRouter, Route, Routes } from 'react-router-dom'
import SignIn from './pages/Signin'
import SignUp from './pages/SignUp'
import LayoutAdmin from './components/Manager/layout'
import AddBook from './pages/admin/ProductManager/AddBook'
import BookList from './pages/admin/ProductManager/BookList'
import BookDetail from './pages/admin/ProductManager/BookDetail'


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/auth' element={<SignIn />} />
        <Route path='/sign-up' element={<SignUp />} />
        <Route path='/manager' element={<LayoutAdmin />}>
          <Route path='book' element={<BookList />} />
          <Route path='book/add' element={<AddBook />} />
          <Route path='book/detail/:id' element={<BookDetail />} />
          {/* <Route path='account' element={<AccountManager />} /> */}
        </Route>
        <Route path='/*' element={<SignIn />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
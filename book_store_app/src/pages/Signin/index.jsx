import Box from '@mui/material/Box'
import SignInForm from './SignInForm'
import Logo from '@/components/Logo'
import AuthActions from './AuthActions'
import background from '@/assets/image/login_background.jpg'
import { deleteAccessToken, deleteRefeshToken } from '@/services/localStorageService'
import { toastError } from '@/utils/toast'

const SignIn = () => {

  deleteAccessToken()
  deleteRefeshToken()

  const search = window.location.search
  const params = new URLSearchParams(search)
  const message = params.get('message')
  if (message) {
    toastError(message)
  }

  return (
    <Box sx={{
      minHeight: '100vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center', padding: '10px 0',
      backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.1)), url(${background})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat'
    }} >
      <Box maxWidth='sx' sx={{
        height: 500,
        minHeight: '500',
        boxShadow: '0 0 10px rgb(182, 180, 180);',
        backgroundColor: 'white',
        borderRadius: '10px'
      }} m={2} px={5}>
        <Box sx={{ display: 'flex', justifyContent: 'center' }} pt={4} pb={1}>
          <Logo />
        </Box>
        <AuthActions />
        <SignInForm />
      </Box>
    </ Box>
  )
}

export default SignIn

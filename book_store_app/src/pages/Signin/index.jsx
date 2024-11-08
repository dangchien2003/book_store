import Container from '@mui/material/Container'
import Box from '@mui/material/Box'
import SignInForm from './SignInForm'
import Logo from '@/components/Logo'
import AuthActions from './AuthActions'

const SignIn = () => {
  return (
    <Container maxWidth='xs' sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', padding: '10px 0' }
    }>
      <Box sx={{
        width: '100%',
        height: 500,
        minHeight: '500',
        boxShadow: '0 0 10px rgb(182, 180, 180);'
      }} m={2} px={5}>
        <Box sx={{ display: 'flex', justifyContent: 'center' }} pt={4} pb={1}>
          <Logo />
        </Box>
        <AuthActions />
        <SignInForm />
      </Box>
    </Container >
  )
}

export default SignIn

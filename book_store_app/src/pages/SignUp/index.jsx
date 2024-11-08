import Container from '@mui/material/Container'
import Box from '@mui/material/Box'
import SignUpForm from './SignUpForm'
import Logo from '@/components/Logo'
import { Stack } from '@mui/material'
import { Link } from 'react-router-dom'

const SignUp = () => {
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
        <Stack direction='row' justifyContent='center' sx={{ color: '#0D6EFD', fontSize: 15 }}>
          <Link to="/auth" style={{ textDecoration: 'none', color: '#0D6EFD' }} >Đăng nhập tài khoản</Link>
        </Stack>
        <SignUpForm />
      </Box>
    </Container >
  )
}

export default SignUp

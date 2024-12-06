import Box from '@mui/material/Box'
import SignUpForm from './SignUpForm'
import Logo from '@/components/Logo'
import { Stack } from '@mui/material'
import { Link } from 'react-router-dom'
import background from '@/assets/image/login_background.jpg'

const SignUp = () => {

  return (
    <Box sx={{
      minHeight: '100vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center', padding: '10px 0', backgroundImage: `url(${background})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat'
    }} >
      <Box sx={{
        height: 500,
        maxWidth: '300px',
        minHeight: '500',
        boxShadow: '0 0 10px rgb(182, 180, 180);',
        backgroundColor: 'white',
        borderRadius: '10px'
      }} m={2} px={5}>
        <Box sx={{ display: 'flex', justifyContent: 'center' }} pt={4} pb={1}>
          <Logo />
        </Box>
        <Stack direction='row' justifyContent='center' sx={{ color: '#0D6EFD', fontSize: 15 }}>
          <Link to="/auth" style={{ textDecoration: 'none', color: '#0D6EFD' }} >Đăng nhập tài khoản</Link>
        </Stack>
        <SignUpForm />
      </Box>
    </Box >
  )
}

export default SignUp

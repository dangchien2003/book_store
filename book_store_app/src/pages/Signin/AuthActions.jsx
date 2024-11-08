import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import { Link } from 'react-router-dom'

const AuthActions = () => {
  return (
    <Stack direction='row' justifyContent='center' sx={{ color: '#0D6EFD', fontSize: 15 }}>
      <Link to="/forget" style={{ textDecoration: 'none', color: '#0D6EFD' }}>Quên mật khẩu</Link>
      <Typography variant='span' mx={1}>|</Typography>
      <Link to="/sign-up" style={{ textDecoration: 'none', color: '#0D6EFD' }} >Đăng ký tài khoản</Link>
    </Stack>
  )
}

export default AuthActions

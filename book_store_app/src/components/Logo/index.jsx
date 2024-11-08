import Box from '@mui/material/Box'
import logoSvg from '@/assets/image/logo.svg'
import Typography from '@mui/material/Typography'

const Logo = () => {
  return (
    <Box sx={{ display: 'flex', justifyItems: 'center' }}>
      <img src={logoSvg} alt='logo' style={{ width: '70px', verticalAlign: 'middle' }} />
      <Typography variant='span' sx={{ fontSize: 35, fontFamily: '"Norican", cursive', lineHeight: '70px' }}>Book Store</Typography>
    </Box>
  )
}

export default Logo

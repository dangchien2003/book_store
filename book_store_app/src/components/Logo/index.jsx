import Box from '@mui/material/Box'
import logoSvg from '@/assets/image/logo.svg'
import Typography from '@mui/material/Typography'

const Logo = ({ isAdmin }) => {
  return (
    <Box sx={{ display: 'flex', justifyItems: 'center' }}>
      <img src={logoSvg} alt='logo' style={{ width: '70px', verticalAlign: 'middle' }} />
      <Box sx={{ position: 'relative', display: { xs: 'none', sm: 'inline' } }}>
        <Typography variant='span' sx={{ fontSize: 35, fontFamily: '"Norican", cursive', lineHeight: '70px' }}>Book Store</Typography>
        {isAdmin && <Typography sx={{ position: 'absolute', left: '50px', bottom: '-3px' }}>Quản trị viên</Typography>}
      </Box>
    </Box>
  )
}

export default Logo

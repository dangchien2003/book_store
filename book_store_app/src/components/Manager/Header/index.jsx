import Logo from '@/components/Logo'
import { Box } from '@mui/material'
import AvatarMenu from './Avatar'

const Header = () => {
  return (
    <Box sx={{ height: '80px', borderBottom: '1px solid black', padding: '0 10px', width: '100%', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
      <Logo isAdmin={true} />
      <AvatarMenu />
    </Box>)
}

export default Header
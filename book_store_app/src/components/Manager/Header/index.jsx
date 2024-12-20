import Logo from '@/components/Logo'
import { Box } from '@mui/material'
import AvatarMenu from './Avatar'

const Header = () => {
  return (
    <Box sx={{ height: '80px', borderBottom: '1px solid #ababab', padding: '0 10px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
      <Logo isAdmin={true} />
      <AvatarMenu />
    </Box>)
}

export default Header
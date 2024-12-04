import { Box } from '@mui/material'
import SidebarMenu from './Menu'
import { Outlet } from 'react-router-dom'
import Header from './Header'
import { ToastContainer } from 'react-toastify'
const LayoutAdmin = () => {
  return (
    <Box sx={{ backgroundColor: '#f7fafa' }}>
      <ToastContainer />
      <Box >
        <Header />
      </Box>
      <Box sx={{
        display: {
          lg: 'flex',
          sm: 'block'
        }
      }}>
        <SidebarMenu />
        <Box sx={{
          borderLeft: '1px solid #ababab',
          padding: '10px', pt: '20px'
        }} flex={1}>
          <Outlet />
        </Box>
      </Box>
    </Box>
  )
}

export default LayoutAdmin
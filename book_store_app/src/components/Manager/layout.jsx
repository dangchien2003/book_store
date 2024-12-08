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
        },
        position: 'relative'
      }}>
        <SidebarMenu />
        <Box sx={{
          borderLeft: {
            lg: '1px solid #ababab',
            xs: 'none'
          },
          padding: '10px',
          // boxSizing: 'border-box',
          pt: '20px',
          position: {
            lg: 'static',
            xs: 'absolute'
          },
          top: 0,
          width: '95vw',
          marginTop: {
            lg: 0,
            xs: '30px'
          }
        }} flex={1}>
          <Outlet />
        </Box>
      </Box>
    </Box>
  )
}

export default LayoutAdmin
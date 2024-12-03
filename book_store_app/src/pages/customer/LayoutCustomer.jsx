import Header from '@/components/Customer/Header'
import { Box } from '@mui/material'
import { Outlet } from 'react-router-dom'

const LayoutCustomer = () => {
  return (
    <Box sx={{ background: '#f0f0f0', minHeight: '100vh', boxSizing: 'border-box' }}>
      <Header />
      <Outlet />
      <Box>footer</Box>
    </Box>
  )
}

export default LayoutCustomer

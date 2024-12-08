import { useState } from 'react'
import Avatar from '@mui/material/Avatar'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import IconButton from '@mui/material/IconButton'
import { Box } from '@mui/material'
import { logoutAccount } from '@/services/authService/authService'
import { getAccessToken, getRefeshToken } from '@/services/localStorageService'
import { toastError } from '@/utils/toast'
import { messageError } from '@/configs/messageError'
import { useNavigate } from 'react-router-dom'

function AvatarMenu() {
  const [anchorEl, setAnchorEl] = useState(null)
  const navigate = useNavigate()

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget)
  }

  const handleClose = (event) => {
    setAnchorEl(!event.currentTarget)
  }

  const handleLogout = () => {
    const refresh = getRefeshToken()
    const access = getAccessToken()
    logoutAccount(access, refresh).catch((error) => {
      toastError(error.response.data ? messageError[error.response.data.code] : error.response.data.message)
    }).finally(() => {
      navigate('/login')
    })
  }

  return (
    <Box sx={{ marginRight: '20px' }}>
      <IconButton onClick={handleClick} sx={{ boxShadow: '0px 4px 6px black', padding: 0 }}>
        <Avatar alt="User Name" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFgZ0Waa7MS6CB8D5IcsbPJMQhKiz1VsZL2w&s" sx={{ width: '45px', height: '45px' }} />
      </IconButton>

      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right'
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right'
        }}
        sx={{
          '& .MuiButtonBase-root': {
            width: '150px'
          },
          zIndex: 9999
        }}
      >
        <MenuItem onClick={handleClose}>Tài khoản</MenuItem>
        <MenuItem onClick={handleLogout}>Đăng xuất</MenuItem>
      </Menu>
    </Box>
  )
}

export default AvatarMenu

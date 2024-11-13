import React, { useState } from 'react'
import Avatar from '@mui/material/Avatar'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import IconButton from '@mui/material/IconButton'
import { Box } from '@mui/material'

function AvatarMenu() {
  const [anchorEl, setAnchorEl] = useState(null)

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget)
  }

  const handleClose = () => {
    setAnchorEl(null)
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
          }
        }}
      >
        <MenuItem onClick={handleClose}>Tài khoản</MenuItem>
        <MenuItem onClick={handleClose}>Đăng xuất</MenuItem>
      </Menu>
    </Box>
  )
}

export default AvatarMenu

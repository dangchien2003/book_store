import React from 'react'
import IconButton from '@mui/material/IconButton'
import Input from '@mui/material/Input'
import InputLabel from '@mui/material/InputLabel'
import InputAdornment from '@mui/material/InputAdornment'
import FormControl from '@mui/material/FormControl'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import { Box } from '@mui/material'

const PasswordForm = ({ password, rePassword, onPasswordChange, onRePasswordChange }) => {
  const [showPassword, setShowPassword] = React.useState(false)

  const handleClickShowPassword = () => setShowPassword((show) => !show)

  return (
    <Box>
      <FormControl sx={{ width: '100%', marginBottom: 1 }} variant="standard">
        <InputLabel htmlFor="standard-adornment-password">Mật khẩu</InputLabel>
        <Input
          id="standard-adornment-password"
          value={password}
          onChange={onPasswordChange}
          type={showPassword ? 'text' : 'password'}
          endAdornment={
            <InputAdornment position="end">
              <IconButton
                aria-label="toggle password visibility"
                onClick={handleClickShowPassword}
              >
                {showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
        />
      </FormControl>
      <FormControl sx={{ width: '100%', marginBottom: 1 }} variant="standard">
        <InputLabel htmlFor="standard-adornment-password">Nhập lại mật khẩu</InputLabel>
        <Input
          id="standard-adornment-password"
          value={rePassword}
          onChange={onRePasswordChange}
          type={showPassword ? 'text' : 'password'}
        />
      </FormControl>
    </Box>
  )
}

export default PasswordForm

import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import { Button, Stack } from '@mui/material'
import { useState } from 'react'
import GoogleSignUp from './GoogleSignUp'
import PasswordForm from './PasswordForm'
import { register } from '@/services/userService'
import { ToastContainer } from 'react-toastify'
import { toastError, toastSuccess } from '@/utils/toast'
import VerifyMessage from './VerifyMessage'
import { messageError } from '@/configs/messageError'

const SignUpForm = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [rePassword, setRePassword] = useState('')
  const [registerSuccess, setRegisterSuccess] = useState(false)

  const handleChangeUsername = (event) => {
    setUsername(event.target.value)
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value)
  }

  const handleChangeRePassword = (event) => {
    setRePassword(event.target.value)
  }

  const handleClickRegister = () => {
    if (!comparePassword())
      toastError('Mật khẩu không trùng khớp')
    else {
      register(username, password)
        .then((response) => {
          toastSuccess('Đăng ký thành công')
          setUsername(response.data.result.email)
          setRegisterSuccess(true)
        }).catch((error) => {
          toastError(messageError[error.response.data.code] ?? error.response.data.message)
        })
    }
  }

  const comparePassword = () => {
    return password === rePassword
  }

  return (
    <Box pt={2}>
      <ToastContainer />
      {!registerSuccess ?
        <Stack gap={1} >
          <TextField
            id="standard-basic"
            label="Email"
            variant="standard"
            sx={{ width: '100%', marginBottom: 1 }}
            onChange={handleChangeUsername}
            value={username}
          />
          <PasswordForm password={password} rePassword={rePassword} onPasswordChange={handleChangePassword} onRePasswordChange={handleChangeRePassword} />
          <Button variant="contained" onClick={handleClickRegister}>Đăng ký</Button>
          <Stack direction='row' justifyContent='center'>
            <GoogleSignUp />
          </Stack>
        </Stack>
        :
        <VerifyMessage email={username} />
      }
    </Box>
  )
}

export default SignUpForm

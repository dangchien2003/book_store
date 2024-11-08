import { Box } from '@mui/material'
import Typography from '@mui/material/Typography'

const VerifyMessage = ({ email }) => {
  return (
    < Box >
      <Typography textAlign='center'>
        Thư xác nhận sẽ được gửi tới {email} trong thời gian sớm nhất
      </Typography>
    </Box >
  )
}

export default VerifyMessage

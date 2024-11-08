import { Checkbox, FormControlLabel } from '@mui/material'
const Remember = ({ onCheck, value }) => {
  const onChangeChecked = (event) => {
    onCheck(event.target.checked)
  }
  return (
    <FormControlLabel
      control={<Checkbox sx={{ transform: 'scale(0.9)' }} checked={value} />}
      label="Ghi nhớ lần sau"
      sx={{ '& .MuiFormControlLabel-label': { fontSize: '14px' } }} onChange={onChangeChecked} />
  )
}

export default Remember

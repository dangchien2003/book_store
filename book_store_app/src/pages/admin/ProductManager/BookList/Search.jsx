import { Box, Button, TextField, Typography } from '@mui/material'

import { Search as SearchIcon } from '@mui/icons-material'
import { useDispatch, useSelector } from 'react-redux'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'
import { incrementWithTimeout } from '@/features/manager/filterBook/managerClickFindBookSlice'

const Search = () => {
  const filter = useSelector((state) => state.managerFilterBook.value)
  const dispatch = useDispatch()

  const handleChangeValue = (event) => {
    dispatch(incrementByFilter({ field: 'name', value: event.target.value }))
  }

  const handleClickFind = () => {
    dispatch(incrementWithTimeout())
  }

  return (
    <Box sx={{ gap: '10px' }}>
      <Typography variant='span' sx={{ mr: '10px' }}>
        Tìm kiếm
      </Typography>
      <TextField id="outlined-basic" variant="outlined" type='text'
        placeholder='Nhập tên sách'
        value={filter.name}
        onChange={handleChangeValue}
        sx={{
          width: '500px', marginRight: '10px', borderRadius: '5px', background: 'white', fontSize: '16px',
          '& .MuiOutlinedInput-root': {
            padding: 0
          },
          '& input': {
            padding: '7px'
          }
        }} />
      <Button variant="outlined" onClick={handleClickFind}><SearchIcon /></Button>
    </Box>
  )
}

export default Search

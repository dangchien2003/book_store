import { Box, Button, Grid, TextField, Typography } from '@mui/material'

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
      <Grid container>
        <Grid item lg={7} md={8} sm={8} sx={8} >
          <Box sx={{ display: 'flex' }}>
            <Typography variant='span' sx={{ mr: '10px' }}>
              Tìm kiếm
            </Typography>
            <TextField id="outlined-basic" variant="outlined" type='text'
              placeholder='Nhập tên sách'
              value={filter.name}
              onChange={handleChangeValue}
              sx={{
                minWidth: {
                  lg: '300px',
                  xs: 'auto'
                },
                flexGrow: 1,
                marginRight: '10px', borderRadius: '5px', background: 'white', fontSize: '16px',
                '& .MuiOutlinedInput-root': {
                  padding: 0
                },
                '& input': {
                  padding: '7px'
                }
              }} />
          </Box>
        </Grid>
        <Grid item lg={5} md={4} sm={4} xs={4}>
          <Button variant="outlined" onClick={handleClickFind}><SearchIcon /></Button>
        </Grid>
      </Grid>
    </Box >
  )
}

export default Search

import Logo from '@/components/Logo'
import { Box, Container, Grid } from '@mui/material'
import Search from './Search'

const Header = () => {
  return (
    <Box sx={{ background: 'white', maxHeight: '70px' }}>
      <Container>
        <Grid container>
          {/* Logo */}
          <Grid item xs={2} sm={2} md={2} lg={3}>
            <Logo />
          </Grid>

          {/* Search */}
          <Grid item xs={7} sm={8} md={8} lg={6}>
            <Search />
          </Grid>

          {/* Actions (hoặc một thành phần khác) */}
          <Grid item xs={3} sm={2} md={2} lg={3}>
            <Search /> {/* Thay thành phần này nếu bạn muốn */}
          </Grid>
        </Grid>
      </Container>
    </Box>
  )
}

export default Header

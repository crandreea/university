# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles/simulare_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/simulare_autogen.dir/ParseCache.txt"
  "simulare_autogen"
  )
endif()

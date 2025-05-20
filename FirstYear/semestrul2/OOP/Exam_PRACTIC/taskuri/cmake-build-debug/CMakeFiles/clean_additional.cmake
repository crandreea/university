# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles/taskuri_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/taskuri_autogen.dir/ParseCache.txt"
  "taskuri_autogen"
  )
endif()

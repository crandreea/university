using Lab10.domain;

namespace Lab10.repository;

public class StudentRepository : FileRepository<int, Student>
{
    public StudentRepository() : base("Students")
    {
    }
}
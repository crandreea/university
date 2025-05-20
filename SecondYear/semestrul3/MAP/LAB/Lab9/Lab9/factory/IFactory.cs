using System.ComponentModel;
using Lab9.model.containers;
using IContainer = Lab9.model.containers.IContainer;

namespace Lab9.factory;

public interface IFactory
{ 
    IContainer createContainer(EStrategy strategy);
}
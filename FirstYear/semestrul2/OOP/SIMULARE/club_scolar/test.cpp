#include "test.h"
#include <assert.h>
void testRepo(){
    RepoElev repo;
    repo.add(Elev (1, "a", "a", {"muzica"}));
    repo.add(Elev (2, "a", "a", {"muzica", "jurnalism"}));
    try {
        repo.add(Elev(1, "a", "a", {"muzica"}));
        assert(false);
    }
    catch (Exception& e){
        assert(e.get_mess().find("Elevul exista") != std::variant_npos);
        assert(true);
    }
}
void testAll(){
    testRepo();
}
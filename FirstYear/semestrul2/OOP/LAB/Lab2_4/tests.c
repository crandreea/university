#include "tests.h"
#include "service.h"
#include "offer.h"

#include <assert.h>
#include <string.h>



//tests offer
void testValidate() {
    Offer *o = create("", "Grecia", "23/08/2025", 3456);
    assert(validate(o) == 1);
    destroy(o);
    Offer *o1 = create("mare", " ", "23/08/2025", 3456);
    assert(validate(o1) == 2);
    destroy(o1);
    Offer *o2 = create("mare", "Grecia", "", 3456);
    assert(validate(o2) == 3);
    destroy(o2);
    Offer *o3 = create("mare", "Grecia", "23/08/2025", -3456);
    assert(validate(o3) == 4);
    destroy(o3);
    Offer *o4 = create("mare", "Grecia", "23/08/2025", 3456);
    assert(validate(o4) == 0);
    destroy(o4);

}
void testValidDate() {
    Offer *o1 = create("mare", "Grecia", "23/00/2005", 3456);
    assert(isValidDate(o1) == 0);
    destroy(o1);
    Offer *o2 = create("mare", "Grecia", "31/04/2005", 3456);
    assert(isValidDate(o2) == 0);
    destroy(o2);
    Offer *o5 = create("mare", "Grecia", "32/03/2005", 3456);
    assert(isValidDate(o5) == 0);
    destroy(o5);
    Offer *o3 = create("mare", "Grecia", "29/02/2021", 3456);
    assert(isValidDate(o3) == 0);
    destroy(o3);
    Offer* o4 = create("mare", "Grecia", "19/02/2021", 3456);
    assert(isValidDate(o4) == 1);
    destroy(o4);
    Offer *o6 = create("mare", "Grecia", "30/02/2020", 3456);
    assert(isValidDate(o6) == 0);
    destroy(o6);
}

void testCreateDestroy() {
    Offer *o = create("mare", "Grecia", "23/08/2025", 3456);
    assert(strcmp(o->type, "mare") == 0);
    assert(strcmp(o->destination, "Grecia") == 0);
    assert(strcmp(o->date, "23/08/2025") == 0);
    assert(o->price == 3456);
    destroy(o);
}

void testOnlyLetters(){
    assert(containsOnlyLetters("...")==0);
}

//tests list

void testCreateList() {
    List * l = create_list();
    assert(size(l) == 0);
    destroy_list(l);
}

void testCopyList(){
    List *l = create_list();
    add(l, create("mare", "Grecia", "20/08/2023", 1000));
    add(l, create("munte", "Austria", "30/01/2024", 2078));

    List * rez = copy_list(l);
    assert(size(rez)==2);

    destroy_list(l);
    destroy_list(rez);
}

void testDelete() {
    List *l = create_list();
    Offer *o = create("mare", "Grecia", "20/08/2023", 1000);
    add(l, o);
    destroy(o);

    add(l, create("munte", "Austria", "30/01/2024", 2078));
    delete(l, 0);
    assert(size(l) == 1);
    destroy_list(l);

}

void testModify() {
    List *l = create_list();
    add(l, create("munte", "Austria", "30/01/2024", 2078));

    Offer *o = create("mare", "Grecia", "20/08/2023", 1000);
    modify(l, 0, o);

    o = get(l,0);
    assert(strcmp(o->type, "mare") == 0);
    destroy_list(l);
}

void testIterateList() {
    List *l = create_list();
    add(l, create("mare", "Grecia", "20/08/2023", 1000));
    add(l, create("munte", "Austria", "30/01/2024", 2078));
    assert(size(l) == 2);
    Offer *o = get(l, 0);
    assert(strcmp(o->type, "mare") == 0);
    destroy_list(l);

}

//tests service

void testAddOffer() {
    List *l = create_list();

    int error = add_offer(l, "", "Grecia", "23/08/2004", 1234);
    assert(error != 0);

    add_offer(l, "mare", "Grecia", "23/08/2004", 12004);
    assert(size(l) == 1);

    error = add_offer(l, "mare", "Grecia", "23/08/2004", 12004);
    assert(error == 1);
    destroy_list(l);

}

void testDeleteOffer() {
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 2078);

    int error = delete_offer(l, "mare", "dhdh", "23/09/2004");
    assert(error != 0);

    error = delete_offer(l, "mare", "Grecia", "20/08/2023");
    assert(error == 0);

    destroy_list(l);
}

void testModifyOffer() {
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 2078);

    int error = modify_offer(l, "", "Grecia", "23/08/2004", 1234);
    assert(error != 0);

    error = modify_offer(l, "mare", "Grecia", "20/08/2023", 1934);
    assert(error == 0);

    error = modify_offer(l, "munte", "Grecia", "20/08/2023", 1934);
    assert(error == 1);

    destroy_list(l);

}

void testMod(){
    assert(validate_mod(1) == 1 && validate_mod(2) == 1);
    assert(validate_mod(0) == 0);
}

void testFilterDestination(){
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 1234);

    List *rez = filter_destination(l, "Grecia");
    assert(size(rez) == 2);

    destroy_list(l);
    destroy_list(rez);

}

void testFilterType(){
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 1234);

    List *rez = filter_type(l, "mare");
    assert(size(rez) == 2);

    destroy_list(l);
    destroy_list(rez);

}

void testFilterPrice(){
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 1234);

    List *rez = filter_price(l, 1234);
    assert(size(rez) == 2);

    destroy_list(l);
    destroy_list(rez);

}


void testSort(){
    List *l = create_list();
    add_offer(l, "mare", "Grecia", "23/08/2004", 1234);
    add_offer(l, "mare", "Grecia", "20/08/2023", 1000);
    add_offer(l, "munte", "Austria", "30/01/2024", 1234);

    List *rez = sort(l, 1);
    assert(rez->elements[0]->price == 1000);
    assert(strcmp(rez->elements[1]->destination,"Austria")==0);
    destroy_list(l);
    destroy_list(rez);
}
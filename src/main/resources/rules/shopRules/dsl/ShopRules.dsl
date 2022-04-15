#/ debug: display result and usage

[when][]greater than or equal to {n}= >= {n}
[when][]less than {n}= < {n}
[when][]- {constraint:.*}={constraint}

[when][]a user is doing a purchase=
    $purchase: Purchase($user: user, $numberOfItems: numberOfItems)

[when][]a user is returning items=
    $itemReturn: ItemReturn($user: user, $numberOfReturns: numberOfReturns)

[when][]a user has=
     $user: User()

[then][]register the purchase=
    log <"User " + $user.getId() + " is purchasing " + $numberOfItems + " items">
    update user purchased items with + $numberOfItems;
    delete( $purchase );

[then][]register the return=
  log <"User " + $user.getId() + " is returning " + $numberOfReturns + " items">
  update user purchased items with - $numberOfReturns;
  delete( $itemReturn );

[then][]issue loyal customer status of level {n}=
    log <"User " + $user.getId() + " has now level {n} discount">
    insertLogical(new LoyalCustomer($user, {n}));

[then][]update user purchased items with {value};=
    $user.setPurchasedItems( $user.getPurchasedItems() {value} );
    update( $user )

[then]log <{msg}>= System.out.println({msg});

# README

## Setting Up the Code

To set up and run the code, follow the [instructions listed here](https://q.utoronto.ca/courses/154572/pages/project-phase-1?module_item_id=1428678). Most importantly, set Phase 1 as your project folder. Ensure that the file ```phase1/out/files/restrictions.csv``` is present (it should already be there). All other necessary files will be generated as the program runs. There is also a .zip file in ```phase1``` with some pre-configured csv files you can use for manual testing. If you wish to use these, you must move these files to ```phase1/out/files```. 

There will be a build error in the test files if you do not add the necessary JUnit packages to your path. If you do not wish to add these packages, we have added an option called ```Main (no test)```to change the run configuration to run despite any build errors in the test folder.

## Running the Code

To run the program, go to the ```Main``` file in ```phase1/src/controllers``` and click *run*. Upon startup, if the program does not detect an ```accounts.csv``` file in ```phase1/out/files```, it will automatically create an admin account with the username "**admin**" and password "**12345**" (this is because having an admin account in the program is necessary to create more admins). 

## User Actions

### Logging In and Creating Users

The first screen you'll see is the login screen, where you'll see the option to log in, create user, and terminate the program:

```latex
Which action would you like to do?
0. Login
1. Create an account
2. Quit
```

(Note that usernames and passwords are case-sensitive). 

Once you've logged in, you'll be taken to the home menu of our program. The home menu changes depending on whether or not you're an admin, and whether or not you're frozen. This is what the menu looks like to an unfrozen user. 

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Logout
```

### Item Browsing

If you wish to see the items available for trade or your items, go to menu option ```Browse the Inventory```: 

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory   <-----
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Logout
```

From there, the first four options allow you to view all items in the system, filtered by different criteria: 

```latex
Choose an option to do:
0. View all approved items               <-----
1. View your approved items              <-----
2. View your pending items               <-----
3. View all items available for trading  <-----
4. Add to wishlist
5. Create a new item   
6. Remove your item from inventory
7. Return to main menu
```

### Adding Items to Your Inventory

After you've logged in, if you want to add an item to your inventory, go to menu option ```Browse the inventory```:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory   <-----
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Logout
```

From there, choose option ```Create a new item```:

```latex
Choose an option to do:
0. View all approved items
1. View your approved items
2. View your pending items
3. View all items available for trading
4. Add to wishlist
5. Create a new item   <-----
6. Remove your item from inventory
7. Return to main menu
```

From there, follow the prompts to create your new item. The item will not be visible until an admin authorizes the item. Note that the inclusion of commas are not allowed in the name or description of the item.

### Removing an item from your inventory

Similar to creating an item, the option to remove an item also happens in ```Browse the Inventory```. Choose option ```Remove your item from inventory```:

```latex
Choose an option to do:
0. View all approved items
1. View your approved items
2. View your pending items
3. View all items available for trading
4. Add to wishlist
5. Create a new item   
6. Remove your item from inventory  <-----
7. Return to main menu
```

From there, you'll be shown a list of all your approved items, and you can enter the number to the left of the item you wish to remove. 

### Adding an Item to Your Wishlist

Another option in ```Browse the Inventory``` is adding an item to your wishlist. Choose option ```Add to wishlist```:

```latex
Choose an option to do:
0. View all approved items
1. View your approved items
2. View your pending items
3. View all items available for trading
4. Add to wishlist   <-----
5. Create a new item   
6. Remove your item from inventory  
7. Return to main menu
```

And you'll be shown the list of all items available for trading, and you can add an item to your wishlist by entering the number to the left of the item.

### Removing an Item from Your Wishlist

To remove an item from your wishlist, go to ```Manage your wishlist``` in the main menu:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory   
2. Manage your wishlist   <-----
3. Initiate a trade with a specific account
4. Logout
```

From there, choose ``` Remove item from wishlist```:

``` latex
0. Start trade.
1. Remove item from wishlist.
2. Back.
Select action:
```

And you'll be shown the items in your wishlist. Enter the number to the left of the item to remove it from your wishlist.

### Starting a Trade

To start a trade, you have two options: trading with a specific user, or trading from an item in your wishlist.

#### Trading With a Specific User

To trade with a specific user, choose ```Initiate a trade with a specific account``` from the main menu. This option is unavailable if you do not own items, you have surpassed the weekly threshold for trades, or your account is frozen:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory   
2. Manage your wishlist   
3. Initiate a trade with a specific account <-----
4. Logout
```

From there, you'll be shown a list of users. Enter the number to the left of a user to trade with them. Note that you can only make a two-way trade or lend an item to a user using this option (you cannot request an item from them and offer nothing in return).

#### Trading From an Item in Your Wishlist

To trade from an item in your wishlist, go to ```Manage your wishlist``` from the main menu:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist  <-----
3. Initiate a trade with a specific account
4. Logout
```

From there, choose ```Start trade```. This will be unavailable if your account is frozen:

```latex
0. Start trade.
1. Remove item from wishlist.
2. Back.
Select action: 
```

From there, you'll be shown a list of items in your wishlist. Enter the number to the left of the item you wish to receive to create a trade with that item. Note that you can only make a two-way trade or borrow from a user using this option (you cannot lend to them without asking for something in return).

### Configuring a Trade

After you create a trade, you'll have to choose if it's a one-way or two-way trade, if it's permanent or temporary, and the time, date, and place. If a trade is temporary, another trade will take place at the same location, exactly 30 days after the first trade. Also, you cannot set a time for a trade that is earlier than the current time.

After the date of a confirmed date has passed, you can go back and mark the trade as completed. Once both people have marked the trade as completed, it will no longer count towards your number of incomplete trades. 
### Managing Trades in Progress

To check on all your trades, go to the main menu and choose ```Manage your existing trades```: 

```latex
Which action would you like to do?
0. Manage your existing trades <-----
1. Browse the inventory
2. Manage your wishlist  
3. Initiate a trade with a specific account
4. Logout
```

From there, choose ```Select a trade to edit```:

```latex
Choose one of the following options: 
0. View your trades  
1. Select a trade to edit <-----
2. View items given away in recent two-way trades
3. View items given away in recent one-way trades
4. View your most frequent trading partners
5. Return to previous menu
```

You'll be shown a list of the trades that you can edit. If you were not the last person to edit the trade, then you can confirm the time and location for the trade, or suggest a new time and location for the trade. You can reject/cancel the trade regardless of whether or not you were the last person to edit it. 

#### Returning a Temporary Trade

Once a temporary trade has been confirmed by both parties, a new permanent trade will be created to signify returning the item back to its original owner. The default time and location of the return trade is exactly 30 days after the original trade at the same location, but it can be edited. 

#### Viewing Your Previous Trade Information

In ```Manage your existing trades```, you can also view all your previous trades (```view all trades```), view the last 3 items you've given away in two-way trades and one way trades (```View items given away in recent two-way trades``` and ```View items given away in recent one-way trades```, respectively), and view your most frequent trading partners (```View your most frequent trading partners```):

```latex
Choose one of the following options: 
0. View your trades                                <-----
1. Select a trade to edit 
2. View items given away in recent two-way trades  <-----
3. View items given away in recent one-way trades  <-----
4. View your most frequent trading partners        <-----
5. Return to previous menu
```

### Request to be Unfrozen

If a user has been frozen, ```Request to be unfrozen``` will appear on their main menu: 

```latex
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Request to be unfrozen   <-----
4. Logout
```

Choosing this will send an unfreeze request to the admins.

## Admin Actions

By default, the admin has more menu options than a typical user. This is what the menu looks like to an admin. 

``` latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Modify the restriction values of the program
5. Manage the frozen accounts
6. Add an admin account
7. Logout
```

### Changing the Global Restriction Values

By default, you can cannot make a one-way trade where you are borrowing unless you've lent **0** or more items than you've borrowed, you become eligible for freezing if you have more than **5** incomplete trades, and you cannot make more than **10** trades in a week. These values can be changed in ```Modify the restriction values of the program```:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Modify the restriction values of the program  <-----
5. Manage the frozen accounts
6. Add an admin account
7. Logout
```

### Freezing/Unfreezing a User

To freeze/unfreeze a user, go to the ```Manage the frozen accounts``` submenu from menu:

```latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Modify the restriction values of the program  
5. Manage the frozen accounts <-----
6. Add an admin account
7. Logout
```

From there, you can choose to freeze or unfreeze a user:

```latex
Which action would you like to do?
0. Freeze users
1. Unfreeze users
2. Return to home
```

Choosing these options will show you a list of users that are eligible for freezing (based on the restrictions), and a list of users who have requested to be unfrozen, respectively. Enter the number to the left of the user to freeze/unfreeze them. 

### Creating Another Admin Account

To create another admin account, choose ```Add an admin account``` from the main menu and input the username and password of the new admin:

``` latex
Which action would you like to do?
0. Manage your existing trades
1. Browse the inventory
2. Manage your wishlist
3. Initiate a trade with a specific account
4. Modify the restriction values of the program  
5. Manage the frozen accounts 
6. Add an admin account   <-----
7. Logout
```

## Extra Notes

- Your username is case-sensitive and restricted to the following characters: a-z, A-Z, 0-9, _
- Your password is case-sensitive and cannot be empty, contain a space, -, ~, or comma
- Your items' names and descriptions cannot include commas
- The location of your meetup cannot include commas
- You cannot confirm a trade that has a current meet up time that has already passed
- You cannot edit a trade's time to be in the past
- Once you've received an item from a trade, you can use it for other trades. It is your responsibility to ensure that you do not give away items that aren't yours
- When viewing your top 3 items in recent trades, we only show items you gave away
- We do not support negative numbers when restricting (as per the specifications)
- Accounts don't automatically get frozen/unfrozen, and admin has to login and do this manually
- The global action to abort an action is "-1"
- Do not have the .csv files open in excel while you're running the program

## Authors

**Name - "Git Name"**

* Catherine Tianlin Xia - "xiaca"
* Ethan Lam - "Ethan Lam" & "TLELam"
* Hai Yang Tang - "mio"
* Isaac Tse - "Isaac"
* Longyu Li (Andrew) - "Andrew Li*"
* Maryam Gohargani - "Maryam Gohargani"
* Michael Sheinman Orenstrakh - "Michael Sheinman Orenstrakh"
* Tairi Goto - "Tairi"

